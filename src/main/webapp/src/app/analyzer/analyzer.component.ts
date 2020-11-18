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

  currencies: Array<string>;

  types: Array<string>;

  analyzerForm: FormGroup;
  invalidAnalyzer: boolean = false;
  constructor(private formBuilder: FormBuilder, private router: Router, private searchService: SearchService) { }

  ngOnInit(): void {

    this.analyzerForm = this.formBuilder.group({
      name: ['', Validators.compose([Validators.required])]
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
        console.log(data.result);
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

}
