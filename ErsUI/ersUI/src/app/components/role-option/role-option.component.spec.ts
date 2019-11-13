import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleOptionComponent } from './role-option.component';

describe('RoleOptionComponent', () => {
  let component: RoleOptionComponent;
  let fixture: ComponentFixture<RoleOptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoleOptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleOptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
