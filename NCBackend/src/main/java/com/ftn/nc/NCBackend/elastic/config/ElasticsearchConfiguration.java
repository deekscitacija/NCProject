package com.ftn.nc.NCBackend.elastic.config;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.env.Environment;
import org.elasticsearch.node.InternalSettingsPreparer;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeValidationException;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.transport.Netty4Plugin;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.ftn.nc.NCBackend.elastic.plugin.SerbianPlugin;


@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ftn.nc.NCBackend.elastic.repository")
public class ElasticsearchConfiguration {
	
	@SuppressWarnings("resource")
	public Client nodeClient() {
		
		File tmpDir = null;
		try {
			tmpDir = new File("/elastic");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Settings settings = Settings.builder()	
		.put("http.enabled", true)
        .put("cluster.name", "mjokNode")
        .put("transport.type", "netty4")
        .put("client.transport.sniff", true)
        .put("path.data", new File(tmpDir, "data").getAbsolutePath())
        .put("path.logs", new File(tmpDir, "logs").getAbsolutePath())
        .put("path.home", tmpDir.getAbsolutePath()).build();
        
        Collection<Class<? extends Plugin>> plugins = new ArrayList<>();
        plugins.add(SerbianPlugin.class);
        plugins.add(Netty4Plugin.class);

        Node node;
		try {
			node = new NodeWithPlugins(InternalSettingsPreparer.prepareEnvironment(settings, null), plugins).start();
			return node.client();
		} catch (NodeValidationException e) {
			e.printStackTrace();
		}
        
		return null;
	}
    
    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() {
        return new ElasticsearchTemplate(nodeClient());
    }

    
	private class NodeWithPlugins extends Node {
	    protected NodeWithPlugins(Environment environment, Collection<Class<? extends Plugin>> list) {
	        super(environment, list);
	    }
	}

}
