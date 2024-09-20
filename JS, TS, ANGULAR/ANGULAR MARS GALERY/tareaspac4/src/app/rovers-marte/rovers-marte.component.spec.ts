import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoversMarteComponent } from './rovers-marte.component';

describe('RoversMarteComponent', () => {
  let component: RoversMarteComponent;
  let fixture: ComponentFixture<RoversMarteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RoversMarteComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RoversMarteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
