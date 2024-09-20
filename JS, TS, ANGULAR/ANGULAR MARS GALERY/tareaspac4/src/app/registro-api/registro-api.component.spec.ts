import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistroApiComponent } from './registro-api.component';

describe('RegistroApiComponent', () => {
  let component: RegistroApiComponent;
  let fixture: ComponentFixture<RegistroApiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegistroApiComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegistroApiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
