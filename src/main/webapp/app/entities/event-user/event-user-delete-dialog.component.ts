import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventUser } from 'app/shared/model/event-user.model';
import { EventUserService } from './event-user.service';

@Component({
  selector: 'jhi-event-user-delete-dialog',
  templateUrl: './event-user-delete-dialog.component.html'
})
export class EventUserDeleteDialogComponent {
  eventUser: IEventUser;

  constructor(protected eventUserService: EventUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.eventUserService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'eventUserListModification',
        content: 'Deleted an eventUser'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-event-user-delete-popup',
  template: ''
})
export class EventUserDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ eventUser }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EventUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.eventUser = eventUser;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/event-user', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/event-user', { outlets: { popup: null } }]);
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
