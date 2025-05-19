import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GraficoDoughnutComponent } from './grafico-doughnut.component';

describe('GraficoDoughnutComponent', () => {
  let component: GraficoDoughnutComponent;
  let fixture: ComponentFixture<GraficoDoughnutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GraficoDoughnutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GraficoDoughnutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
