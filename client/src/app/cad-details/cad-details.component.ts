import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NgForm} from '@angular/forms';
import {Observable} from 'rxjs';
import {CadObject} from 'src/model/CadObject';
import {CadService} from '../services/cad.service';

export interface Container {
  id?: number,
  farm: string,
  cadNumber: string,
  typeOwn: string,
  tenancy: Date,
  area: number,
  cadCost: number,
  agroArea: number,
  shareOwn: number,
  shareRent: number,
  farmRent: number,
  afOwnerArea: number,
  notCultivated: number,
  notDocument: number,
  cause: string,
  note1: string,
  archiveStatus: boolean
}

@Component({
  selector: 'app-cad-details',
  templateUrl: './cad-details.component.html',
  styleUrls: ['./cad-details.component.css'],
  providers: [CadService]
})
export class CadDetailsComponent {

  container: Container | undefined;
  cadnumber: string = '';
  editnumber: string = '';

  cadEdit: CadObject = new CadObject;

  cad!: CadObject;

  cad2!: CadObject;

  constructor(private cadService: CadService) {
  }

  getObjByNumber(cadnum: string): Observable<CadObject> {
    // @ts-ignore
    return this.cadService.getObjectByCadNumber(cadnum).subscribe(data => {
      this.cad = data
    });
  }

  submit(form: NgForm) {
    // @ts-ignore
    this.cadService.getObjectByCadNumber(this.cadnumber).subscribe(data => {
      this.cad = data
    });
    console.log(this.cad)
    this.cadEdit = this.cad;
  }

  onEdit(editCad: CadObject) {
    this.cad = editCad;
    console.log(this.cad);
    this.cadService.updateCadObject(this.cad);
    console.log('Done');
  }

  addObject(cad: CadObject) {
    this.cadService.addCadObject(cad);
    console.log("Done!!!")
  }
}
