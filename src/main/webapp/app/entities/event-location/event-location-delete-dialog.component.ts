import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventLocation } from 'app/shared/model/event-location.model';
import { EventLocationService } from './event-location.service';

@Component({
  selector: 'jhi-event-location-delete-dialog',
  templateUrl: './event-location-delete-dialog.component.html'
})
export class EventLocationDeleteDialogComponent {
  eventLocation: IEventLocation;

  constructor(
    protected eventLocationService: EventLocationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.eventLocationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'eventLocationListModification',
        content: 'Deleted an eventLocation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-event-location-delete-popup',
  template: ''
})
export class EventLocationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ eventLocation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EventLocationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.eventLocation = eventLocation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/event-location', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/event-location', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
