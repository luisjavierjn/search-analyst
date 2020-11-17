import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { InitialSetupComponent } from './initial-setup/initial-setup.component';
import { AppRouting } from "./app.routing";

@NgModule({
  declarations: [
    AppComponent,
    InitialSetupComponent
  ],
  imports: [
    BrowserModule,
    AppRouting,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
