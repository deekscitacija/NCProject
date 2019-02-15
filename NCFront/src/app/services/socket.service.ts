import { Injectable } from '@angular/core';

@Injectable()
export class SocketService {

  private socket: WebSocket;

  constructor() { }

  public getSocket() : WebSocket{
    return this.socket;
  }

  public initSocket(): void {
    this.socket = new WebSocket('ws://localhost:8077/websocket');
  }

  public closeSocket(): void {
    this.socket.close();
  }

  public send(message: any): void {
    this.socket.send(JSON.stringify(message));
  }

}
