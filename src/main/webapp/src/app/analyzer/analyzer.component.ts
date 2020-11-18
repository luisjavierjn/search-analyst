import { Component, OnInit } from '@angular/core';
import { Company } from '../models/company.model';

@Component({
  selector: 'app-analyzer',
  templateUrl: './analyzer.component.html',
  styleUrls: ['./analyzer.component.css']
})
export class AnalyzerComponent implements OnInit {

  locations = ['Colombia', 'Estados Unidos', 'Canada', 'Panama'];

  model = new Company("abc", 'Software Developer', "USD", 'yes');

  submitted = false;

  onSubmit() { this.submitted = true; }

  constructor() { }

  ngOnInit(): void {
  }

  // TODO: Remove this when we're done
  get diagnostic() { return JSON.stringify(this.model); }

}
