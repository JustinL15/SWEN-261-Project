import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StarredProductsComponent } from './starred-products.component';

describe('StarredProductsComponent', () => {
  let component: StarredProductsComponent;
  let fixture: ComponentFixture<StarredProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StarredProductsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StarredProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
