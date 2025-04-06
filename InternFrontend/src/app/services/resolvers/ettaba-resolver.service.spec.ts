import { TestBed } from '@angular/core/testing';

import { EttabaResolverService } from './ettaba-resolver.service';

describe('EttabaResolverService', () => {
  let service: EttabaResolverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EttabaResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
