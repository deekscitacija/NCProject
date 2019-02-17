import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalizaRecenzijaComponent } from './analiza-recenzija.component';

describe('AnalizaRecenzijaComponent', () => {
  let component: AnalizaRecenzijaComponent;
  let fixture: ComponentFixture<AnalizaRecenzijaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnalizaRecenzijaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnalizaRecenzijaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
