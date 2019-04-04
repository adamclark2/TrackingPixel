import { Component, OnInit, OnChanges } from '@angular/core';
import Chart from 'chart.js';
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-pixel-detail',
  templateUrl: './pixel-detail.component.html',
  styleUrls: ['./pixel-detail.component.css']
})
export class PixelDetailComponent implements OnInit, OnChanges {

  constructor(private route: ActivatedRoute) {}

  pixelID = -1
  pixelExists = false
  doCreatePixel = false

  categories = ["Fun", "Awesome", "Cool"]

  chart
  myBarChart

  ngOnInit() {
    this.pixelID = this.route.snapshot.params['id']
    this.pixelExists = this.pixelID == 1;
    this.drawChart()
  }

  drawChart(){
    var data = {
        labels: [
            "Value A","Value B"],
        datasets: [
            {
                "data": [101342, 55342],   // Example data
                "backgroundColor": [
                    "#1fc8f8",
                    "#76a346"
                ]
            }]
    };

    this.chart = new Chart(
        'donut',
        {
            "type": 'doughnut',
            "data": data,
            "options": {
                "cutoutPercentage": 50,
                "animation": {
                    "animateScale": true,
                    "animateRotate": false
                }
            }
        }
    );

    var barData = {
      labels: ["One", "Two"],
      datasets: [
        {
          data: [100, 50],
          backgroundColor: ["blue", "red"]
        },
      ]
    };
    
    this.myBarChart = new Chart('bar', {
      "type": 'bar',
      "data": barData,
      options: {
        scales: {
            yAxes: [{
              ticks: {
                min: 0,
                stepSize: 10,
              }
            }]
        }
      }
    });
  }

  btnCreate(){
    this.doCreatePixel = true
  }

}
