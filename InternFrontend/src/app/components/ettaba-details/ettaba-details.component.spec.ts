import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EttabaDetailsComponent } from './ettaba-details.component';

describe('EttabaDetailsComponent', () => {
  let component: EttabaDetailsComponent;
  let fixture: ComponentFixture<EttabaDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EttabaDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EttabaDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
