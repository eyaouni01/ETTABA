import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EttabaComponent } from './ettaba.component';

describe('EttabaComponent', () => {
  let component: EttabaComponent;
  let fixture: ComponentFixture<EttabaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EttabaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EttabaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
