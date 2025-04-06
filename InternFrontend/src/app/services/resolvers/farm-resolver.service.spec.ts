import { TestBed } from '@angular/core/testing';

import { FarmResolverService } from './farm-resolver.service';

describe('FarmResolverService', () => {
  let service: FarmResolverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FarmResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
