import { TestBed } from '@angular/core/testing';

import { EttabaService } from './ettaba.service';

describe('EttabaService', () => {
  let service: EttabaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EttabaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
