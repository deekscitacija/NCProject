import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes, Router } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule,  MatCardModule} from '@angular/material';

import { AppComponent } from './app.component';

import { CasopisService } from './services/casopis.service';
import { SearchService } from './services/search.service';
import { LoginService } from './services/login.service';
import { TokenService } from './services/token.service';
import { RadService } from './services/rad.service';

import { ListToStringPipe } from './pipes/list-to-string.pipe';
import { YesNoPipe } from './pipes/yes-no.pipe';
import { SubstringPipe } from './pipes/substring.pipe';
import { DateDisplayPipe } from './pipes/date-display.pipe';

import { WelcomeComponent } from './components/welcome/welcome.component';
import { MagazineListComponent } from './components/magazine-list/magazine-list.component';
import { ErrorComponent } from './components/error/error.component';
import { SearchPanelComponent } from './components/search-panel/search-panel.component';
import { ResultViewComponent } from './components/search-panel/result-view/result-view.component';
import { MagazinePreviewComponent } from './components/magazine-preview/magazine-preview.component';
import { SearchDialogComponent } from './components/dialogs/search-dialog/search-dialog.component';
import { GeoSearchComponent } from './components/search-panel/geo-search/geo-search.component';
import { NewPaperComponent } from './components/new-paper/new-paper.component';
import { IzdanjePreviewComponent } from './components/izdanje-preview/izdanje-preview.component';
import { PaperPreviewComponent } from './components/paper-preview/paper-preview.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';



@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    MagazineListComponent,
    ListToStringPipe,
    YesNoPipe,
    ErrorComponent,
    SearchPanelComponent,
    ResultViewComponent,
    SubstringPipe,
    MagazinePreviewComponent,
    SearchDialogComponent,
    GeoSearchComponent,
    NewPaperComponent,
    DateDisplayPipe,
    IzdanjePreviewComponent,
    PaperPreviewComponent,
    UserProfileComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule, 
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
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
        path : 'naucna-centrala.com/pretraga/geo',
        component : GeoSearchComponent
      },
      {
        path : 'naucna-centrala.com/casopis/:id',
        component : MagazinePreviewComponent
      },
      {
        path : 'naucna-centrala.com/izdanje/:id',
        component : IzdanjePreviewComponent
      },
      {
        path : 'naucna-centrala.com/novi-rad',
        component : NewPaperComponent
      },
      {
        path : 'naucna-centrala.com/profil',
        component : UserProfileComponent
      },
      { 
        path: '**', 
        component: ErrorComponent 
      }
    ])
  ],
  providers: [
    CasopisService,
    SearchService,
    LoginService,
    TokenService,
    RadService
  ],
  entryComponents: [SearchDialogComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
