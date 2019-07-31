import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventAttendance } from 'app/shared/model/event-attendance.model';
import { EventAttendanceService } from './event-attendance.service';

@Component({
  selector: 'jhi-event-attendance-delete-dialog',
  templateUrl: './event-attendance-delete-dialog.component.html'
})
export class EventAttendanceDeleteDialogComponent {
  eventAttendance: IEventAttendance;

  constructor(
    protected eventAttendanceService: EventAttendanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.eventAttendanceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'eventAttendanceListModification',
        content: 'Deleted an eventAttendance'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-event-attendance-delete-popup',
  template: ''
})
export class EventAttendanceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ eventAttendance }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EventAttendanceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.eventAttendance = eventAttendance;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/event-attendance', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/event-attendance', { outlets: { popup: null } }]);
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
