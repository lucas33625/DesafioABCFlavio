import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GraficoLineComponent } from './grafico-line.component';

describe('GraficoLineComponent', () => {
  let component: GraficoLineComponent;
  let fixture: ComponentFixture<GraficoLineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GraficoLineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GraficoLineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
