import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { Company } from '../models/company.model';
import { Totals } from '../models/totals.model';
import { Circle } from '../models/circle.model';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {SearchService} from "../services/search.service";

@Component({
  selector: 'app-analyzer',
  templateUrl: './analyzer.component.html',
  styleUrls: ['./analyzer.component.css']
})
export class AnalyzerComponent implements OnInit {
  @ViewChild('canvas', { static: true })
  canvas: ElementRef<HTMLCanvasElement>;

  private ctx: CanvasRenderingContext2D;

  currencies: Array<string>;
  types: Array<string>;
  total: Totals;

  circleNames: Circle = new Circle;
  circleCurrencies: Circle = new Circle;
  circleTypes: Circle = new Circle;

  analyzerForm: FormGroup;
  invalidAnalyzer: boolean = false;
  constructor(private formBuilder: FormBuilder, private router: Router, private searchService: SearchService) { }

  ngOnInit(): void {
    this.ctx = this.canvas.nativeElement.getContext('2d');
    //this.draw();

    this.analyzerForm = this.formBuilder.group({
      name: ['', Validators.compose([Validators.required])],
      currency: ['', Validators.compose([Validators.required])],
      type: ['', Validators.compose([Validators.required])]
    });

    this.searchService.getCurrencies().subscribe(data => {
      if(data.status === 200) {
        //console.log(data.result);
        this.currencies = data.result;
      }else {
        this.invalidAnalyzer = true;
        alert(data.message);
      }
    });

    this.searchService.getTypes().subscribe(data => {
      if(data.status === 200) {
        //console.log(data.result);
        this.types = data.result;
      }else {
        this.invalidAnalyzer = true;
        alert(data.message);
      }
    });

  }

  onSubmit() {
    if (this.analyzerForm.invalid) {
      return;
    }

    const payload = {
      name: this.analyzerForm.controls.name.value,
      currency: this.analyzerForm.controls.currency.value,
      type: this.analyzerForm.controls.type.value
    }

    this.searchService.getTotals(payload).subscribe(data => {
      if(data.status === 200) {
        this.total = data.result;

        this.draw();
        /*
        this.searchService.getTotals(payload).subscribe(info => {
          if(info.status === 200) {
            console.log(info.result);

          }else {
            this.invalidAnalyzer = true;
            alert(info.message);
          }
        });
        */
      }else {
        this.invalidAnalyzer = true;
        alert(data.message);
      }
    });
  }

  getInitialSetup(): void {
      this.router.navigate(['']);
  }

  draw(): void {
    this.ctx.clearRect(0,0,800,800);

    this.ctx.font = "20px Arial";
    this.ctx.textBaseline = 'middle';
    this.ctx.textAlign = 'center';
    const x = (this.canvas.nativeElement as HTMLCanvasElement).width / 2;
    const y = (this.canvas.nativeElement as HTMLCanvasElement).height / 2;
    this.ctx.fillText("@luisjavierjn", x, y);

    var mytotal = Math.max(Math.max(this.total.totalCompaniesByName,this.total.totalCompaniesByCurrency),this.total.totalCompaniesByType);
    var percN = this.total.totalCompaniesByName / mytotal;
    var percC = this.total.totalCompaniesByCurrency / mytotal;
    var percT = this.total.totalCompaniesByType / mytotal;

    this.circleNames.centerX = 200;
    this.circleNames.centerY = 200;
    this.circleNames.radius = 180 * percN;
    this.ctx.fillText(this.total.totalCompaniesByName + "", this.circleNames.centerX, this.circleNames.centerY);

    this.circleCurrencies.centerX = 600;
    this.circleCurrencies.centerY = 200;
    this.circleCurrencies.radius = 180 * percC;
    this.ctx.fillText(this.total.totalCompaniesByCurrency + "", this.circleCurrencies.centerX, this.circleCurrencies.centerY);

    this.circleTypes.centerX = 400;
    this.circleTypes.centerY = 600;
    this.circleTypes.radius = 180 * percT;
    this.ctx.fillText(this.total.totalCompaniesByType + "", this.circleTypes.centerX, this.circleTypes.centerY);

    this.ctx.globalAlpha = 0.5;

    this.ctx.beginPath();
    this.ctx.arc(this.circleNames.centerX, this.circleNames.centerY, this.circleNames.radius, 0, 2 * Math.PI, false);
    this.ctx.fillStyle = 'green';
    this.ctx.fill();
    this.ctx.lineWidth = 1;
    this.ctx.strokeStyle = '#003300';
    this.ctx.stroke();

    this.ctx.beginPath();
    this.ctx.arc(this.circleCurrencies.centerX, this.circleCurrencies.centerY, this.circleCurrencies.radius, 0, 2 * Math.PI, false);
    this.ctx.fillStyle = 'red';
    this.ctx.fill();
    this.ctx.lineWidth = 1;
    this.ctx.strokeStyle = '#003300';
    this.ctx.stroke();

    this.ctx.beginPath();
    this.ctx.arc(this.circleTypes.centerX, this.circleTypes.centerY, this.circleTypes.radius, 0, 2 * Math.PI, false);
    this.ctx.fillStyle = 'blue';
    this.ctx.fill();
    this.ctx.lineWidth = 1;
    this.ctx.strokeStyle = '#003300';
    this.ctx.stroke();
  }

}
