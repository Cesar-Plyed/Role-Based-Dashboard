import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Register } from './register';

describe('Register', () => {
  let component: Register;
  let fixture: ComponentFixture<Register>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Register],
    }).compileComponents();

    fixture = TestBed.createComponent(Register);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should bind the role field to the form control name used by the component', () => {
    expect(() => fixture.detectChanges()).not.toThrow();
    const select = fixture.nativeElement.querySelector('select[formcontrolname="role"]');
    expect(select).not.toBeNull();
  });
});
