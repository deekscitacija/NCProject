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
import { TransakcijaService } from './services/transakcija.service';
import { PaymentService } from './services/payment.service';
import { ProcessEngineService } from './services/process-engine.service';
import { RecenzijaService } from './services/recenzija.service';
import { SocketService } from './services/socket.service';

import { ListToStringPipe } from './pipes/list-to-string.pipe';
import { YesNoPipe } from './pipes/yes-no.pipe';
import { SubstringPipe } from './pipes/substring.pipe';
import { DateDisplayPipe } from './pipes/date-display.pipe';
import { RevizijaStatusPipe } from './pipes/revizija-status.pipe';
import { TransactionTypePipe } from './pipes/transaction-type.pipe';

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
import { TransactionPanelComponent } from './components/user-profile/transaction-panel/transaction-panel.component';
import { SuccessComponent } from './components/success/success.component';
import { FailComponent } from './components/fail/fail.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { RegisterComponent } from './components/register/register.component';
import { TaskViewComponent } from './components/user-profile/task-view/task-view.component';
import { PaperInitialApprovalComponent } from './components/paper-initial-approval/paper-initial-approval.component';
import { UploadOpetComponent } from './components/upload-opet/upload-opet.component';
import { IzaberiRecenzenteComponent } from './components/izaberi-recenzente/izaberi-recenzente.component';
import { RecenzijaPanelComponent } from './components/recenzija-panel/recenzija-panel.component';
import { KomentarComponentComponent } from './components/komentar-component/komentar-component.component';
import { AnalizaRecenzijaComponent } from './components/analiza-recenzija/analiza-recenzija.component';
import { PonovoPregledajComponent } from './components/ponovo-pregledaj/ponovo-pregledaj.component';




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
    UserProfileComponent,
    TransactionPanelComponent,
    TransactionTypePipe,
    SuccessComponent,
    FailComponent,
    NotFoundComponent,
    RegisterComponent,
    TaskViewComponent,
    PaperInitialApprovalComponent,
    UploadOpetComponent,
    IzaberiRecenzenteComponent,
    RecenzijaPanelComponent,
    KomentarComponentComponent,
    AnalizaRecenzijaComponent,
    RevizijaStatusPipe,
    PonovoPregledajComponent
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
        path : 'naucna-centrala.com/registracija',
        component : RegisterComponent
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
        path : 'naucna-centrala.com/pregledaj-rad',
        component : PaperInitialApprovalComponent
      },
      {
        path : 'naucna-centrala.com/upload-revizija',
        component : UploadOpetComponent
      },
      {
        path : 'naucna-centrala.com/recenzija-panel',
        component : RecenzijaPanelComponent
      },
      {
        path : 'naucna-centrala.com/recenzije-analiza',
        component : AnalizaRecenzijaComponent
      },
      {
        path : 'naucna-centrala.com/pregledanje-ponovo',
        component : PonovoPregledajComponent
      },
      {
        path : 'naucna-centrala.com/izaberi-recenzente',
        component : IzaberiRecenzenteComponent
      },
      {
        path : 'naucna-centrala.com/profil',
        component : UserProfileComponent
      },
      {
        path : 'naucna-centrala.com/payment-success',
        component : SuccessComponent
      },
      {
        path : 'naucna-centrala.com/payment-error',
        component : ErrorComponent
      },
      {
        path : 'naucna-centrala.com/payment-fail',
        component : FailComponent
      },
      { 
        path: '**', 
        component: NotFoundComponent 
      }
    ])
  ],
  providers: [
    CasopisService,
    SearchService,
    LoginService,
    TokenService,
    RadService,
    TransakcijaService,
    PaymentService,
    ProcessEngineService,
    RecenzijaService,
    SocketService
  ],
  entryComponents: [SearchDialogComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
