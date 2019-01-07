import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes, Router } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { WelcomeComponent } from './components/welcome/welcome.component';

import { CasopisService } from './services/casopis.service';
import { MagazineListComponent } from './components/magazine-list/magazine-list.component';
import { ListToStringPipe } from './pipes/list-to-string.pipe';
import { YesNoPipe } from './pipes/yes-no.pipe';
import { ErrorComponent } from './components/error/error.component';
import { SearchPanelComponent } from './components/search-panel/search-panel.component';
import { SadrzajInputComponent } from './components/search-panel/sadrzaj-input/sadrzaj-input.component';


@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    MagazineListComponent,
    ListToStringPipe,
    YesNoPipe,
    ErrorComponent,
    SearchPanelComponent,
    SadrzajInputComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule, 
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot([
      {
        path : '',
        redirectTo: '/naucna-centrala.com',
        pathMatch: 'full'
      },
      {
        path : 'naucna-centrala.com',
        component : WelcomeComponent
      },
      {
        path : 'naucna-centrala.com/pretraga',
        component : SearchPanelComponent
      },
      { 
        path: '**', 
        component: ErrorComponent 
      }
    ])
  ],
  providers: [
    CasopisService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
