import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { autGuard } from './aut.guard';

describe('autGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => autGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
