import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {CadcomponentComponent} from './cadcomponent/cadcomponent.component';
import {OwnerComponent} from './owner/owner.component';
import {CadDetailsComponent} from './cad-details/cad-details.component';
import {CustomDatePipe} from './pipe/custom-date.pipe';
import {UploadFileComponent} from './upload-file/upload-file.component';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    CadcomponentComponent,
    OwnerComponent,
    CadDetailsComponent,
    CustomDatePipe,
    UploadFileComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
