import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js';
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-pixel-detail',
  templateUrl: './pixel-detail.component.html',
  styleUrls: ['./pixel-detail.component.css']
})
export class PixelDetailComponent implements OnInit {

  constructor(private route: ActivatedRoute) {}

  pixelID = -1
  ngOnInit() {
    this.pixelID = this.route.snapshot.params['id']
    let donut = document.getElementById("donut") as HTMLCanvasElement
    let donutCtx = donut.getContext('2d');

    let bar = document.getElementById("bar") as HTMLCanvasElement
    let barCtx = bar.getContext('2d');

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

    var chart = new Chart(
        donutCtx,
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
    var myBarChart = new Chart(barCtx, {
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

}
