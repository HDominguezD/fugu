import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { CityComponent } from './city/city.component';
import { MainComponent } from './main/main.component';
import { routing } from './app.routing';
import { RouterModule } from '@angular/router';
import { SearchWebComponent } from './search-web/search-web.component';

@NgModule({
  declarations: [
    AppComponent,
    CityComponent,
    MainComponent,
    SearchWebComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule,
    routing
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
