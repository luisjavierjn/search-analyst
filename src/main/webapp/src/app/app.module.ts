import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { InitialSetupComponent } from './initial-setup/initial-setup.component';

@NgModule({
  declarations: [
    AppComponent,
    InitialSetupComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
