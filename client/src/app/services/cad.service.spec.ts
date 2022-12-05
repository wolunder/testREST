import { TestBed } from '@angular/core/testing';

import { CadService } from './cad.service';

describe('CadService', () => {
  let service: CadService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CadService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
