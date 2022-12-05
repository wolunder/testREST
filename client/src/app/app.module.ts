import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';


import { AppComponent } from './app.component';
import { CadcomponentComponent } from './cadcomponent/cadcomponent.component';
import { OwnerComponent } from './owner/owner.component';
import { CadDetailsComponent } from './cad-details/cad-details.component';

@NgModule({
  declarations: [
    AppComponent,
    CadcomponentComponent,
    OwnerComponent,
    CadDetailsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
