import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {SearchService} from "../services/search.service";

@Component({
  selector: 'app-initial-setup',
  templateUrl: './initial-setup.component.html',
  styleUrls: ['./initial-setup.component.css']
})
export class InitialSetupComponent implements OnInit {

  initialSetupForm: FormGroup;
  invalidInitialSetup: boolean = false;
  constructor(private formBuilder: FormBuilder, private router: Router, private searchService: SearchService) { }

  onSubmit() {
    if (this.initialSetupForm.invalid) {
      return;
    }
    const payload = {
      offset: this.initialSetupForm.controls.offset.value,
      size: this.initialSetupForm.controls.size.value,
      aggregate: false
    }
    this.searchService.getCompaniesByInitialSetup(payload).subscribe(data => {
      if(data.status === 200) {
        console.log(data.result);
        this.router.navigate(['analyzer']);
      }else {
        this.invalidInitialSetup = true;
        alert(data.message);
      }
    });
  }

  ngOnInit(): void {
    this.initialSetupForm = this.formBuilder.group({
      offset: ['0', Validators.compose([Validators.required])],
      size: ['10', Validators.required]
    });
  }

}
