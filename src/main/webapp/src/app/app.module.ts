import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { InitialSetupComponent } from './initial-setup/initial-setup.component';
import { AppRouting } from "./app.routing";
import {ReactiveFormsModule} from "@angular/forms";
import { SearchService } from './services/search.service';
import {HttpClientModule} from "@angular/common/http";
import { AnalyzerComponent } from './analyzer/analyzer.component';

@NgModule({
  declarations: [
    AppComponent,
    InitialSetupComponent,
    AnalyzerComponent
  ],
  imports: [
    BrowserModule,
    AppRouting,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [SearchService],
  bootstrap: [AppComponent]
})
export class AppModule { }
