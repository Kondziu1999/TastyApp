import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-recipe-summary',
  templateUrl: './recipe-summary.component.html',
  styles: [
  ]
})
export class RecipeSummaryComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    // this.route.queryParams.subscribe(params =>{
      
    // })
  }

}
