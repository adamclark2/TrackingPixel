import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PixelDetailComponent } from './pixel-detail.component';

describe('PixelDetailComponent', () => {
  let component: PixelDetailComponent;
  let fixture: ComponentFixture<PixelDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PixelDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PixelDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
