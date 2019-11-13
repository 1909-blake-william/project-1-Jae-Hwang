import { TestBed } from '@angular/core/testing';

import { ReimbService } from './reimb.service';

describe('ReimbService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ReimbService = TestBed.get(ReimbService);
    expect(service).toBeTruthy();
  });
});
