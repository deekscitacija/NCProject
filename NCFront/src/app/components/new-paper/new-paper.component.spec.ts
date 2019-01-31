import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewPaperComponent } from './new-paper.component';

describe('NewPaperComponent', () => {
  let component: NewPaperComponent;
  let fixture: ComponentFixture<NewPaperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewPaperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewPaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
