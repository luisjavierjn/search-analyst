import { Component, OnInit } from '@angular/core';
import { Company } from '../models/company.model';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {SearchService} from "../services/search.service";

@Component({
  selector: 'app-analyzer',
  templateUrl: './analyzer.component.html',
  styleUrls: ['./analyzer.component.css']
})
export class AnalyzerComponent implements OnInit {

  currencies = ['[Select]' ,'USD$', 'COP$', 'MXN$'];

  locations = ['[Select]' ,'Colombia', 'Estados Unidos', 'Canada', 'Panama'];

  submitted = false;

  onSubmit() { this.submitted = true; }

  analyzerForm: FormGroup;
  invalidAnalyzer: boolean = false;
  constructor(private formBuilder: FormBuilder, private router: Router, private searchService: SearchService) { }

  ngOnInit(): void {
    this.analyzerForm = this.formBuilder.group({
      name: ['', Validators.compose([Validators.required])]
    });
  }

  getInitialSetup(): void {
      this.router.navigate(['']);
  }

}
