import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadDetailsComponent } from './cad-details.component';

describe('CadDetailsComponent', () => {
  let component: CadDetailsComponent;
  let fixture: ComponentFixture<CadDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CadDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CadDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
