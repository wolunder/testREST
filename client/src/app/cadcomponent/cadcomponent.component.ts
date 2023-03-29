import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {CadObject} from 'src/model/CadObject';
import {CadService} from '../services/cad.service';

interface CadObjects {
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
    selector: 'app-cadcomponent',
    templateUrl: './cadcomponent.component.html',
    styleUrls: ['./cadcomponent.component.css'],
    providers: [CadService]
})
export class CadcomponentComponent implements OnInit {

    cadObjectsList: CadObject[] | undefined;
    cads: CadObject [] = []
    cadObj: CadObject | undefined;

    indexId: number = 0;


    constructor(private cadService: CadService) {
    }

    // ngOnInit(): {
    //   // @ts-ignore
    //   this.cadService.getAllCad().subscribe(data =>{
    //     this.cadObjectsList = data;
    //   });
    // }

    ngOnInit() {
        this.cadService.getAllCad().subscribe(data => {
            this.cads = data;
        });
    }

    removeUserCadObject(cadObj: CadObject): void {
        // @ts-ignore
        this.cadService.deleteUserCadObject(cadObj);
    }


}
