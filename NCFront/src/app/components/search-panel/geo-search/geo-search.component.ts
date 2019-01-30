import { Component, OnInit } from '@angular/core';
import { SearchService } from '../../../services/search.service';

@Component({
  selector: 'app-geo-search',
  templateUrl: './geo-search.component.html',
  styleUrls: ['./geo-search.component.css']
})
export class GeoSearchComponent implements OnInit {

  private cities: any[] = [];
  private results: any[] = [];
  private selectedCity: number = -1;

constructor(private searchService: SearchService) { }

  ngOnInit() {
    this.getAllCities();
  }

  getAllCities = function(){

    this.searchService.getAllCities().subscribe(
      (res: any) => {
        this.cities = res;
      },
      (error) => {
        alert("Greska!");
      }
    );

  }

  selectCity = function(idx: number){
    this.selectedCity = idx;
  }

  geoSearch = function(){

    this.searchService.geoSearch(this.selectedCity).subscribe(
      (res: any) => {
        this.results = res;
      },
      (error: any) => {
        alert("Greska!");
      }
    );
  }

}
