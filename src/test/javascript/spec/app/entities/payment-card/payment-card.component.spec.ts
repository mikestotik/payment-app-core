import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PaymentAppCoreTestModule } from '../../../test.module';
import { PaymentCardComponent } from 'app/entities/payment-card/payment-card.component';
import { PaymentCardService } from 'app/entities/payment-card/payment-card.service';
import { PaymentCard } from 'app/shared/model/payment-card.model';

describe('Component Tests', () => {
  describe('PaymentCard Management Component', () => {
    let comp: PaymentCardComponent;
    let fixture: ComponentFixture<PaymentCardComponent>;
    let service: PaymentCardService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentAppCoreTestModule],
        declarations: [PaymentCardComponent],
      })
        .overrideTemplate(PaymentCardComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentCardComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentCardService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PaymentCard(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paymentCards && comp.paymentCards[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
