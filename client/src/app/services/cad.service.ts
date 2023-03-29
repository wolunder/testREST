import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {CadObject} from 'src/model/CadObject';


const CAD_API = 'http://localhost:9090/api/cad/'

// @Injectable({
//   providedIn: 'root'
// })
@Injectable()
export class CadService {
    cad?: CadObject;
    indexId: string = '';

    constructor(private http: HttpClient) {
    }

    getAllCad(): Observable<CadObject[]> {
        console.log(CAD_API + 'get-all');
        return this.http.get<CadObject[]>(CAD_API + 'get-all');
    }

    getObjectByCadNumber(cadnumber: string): Observable<CadObject> {
        return this.http.get<CadObject>(CAD_API + 'getNumber/' + cadnumber);
    }

    updateCadObject(obj: CadObject) {
        console.log(CAD_API + 'getNumber/' + obj.id + '/update-cad');
        this.http.post<CadObject>(CAD_API + 'getNumber/' + obj.id + '/update-cad', obj)
            .subscribe(response => this.cad = obj);
    }

    addCadObject(obj: CadObject): Observable<CadObject> {
        console.log(CAD_API + '/add-object');
        return this.http.post<CadObject>(CAD_API + '/add-object', obj);
    }

    deleteUserCadObject(cadObj: CadObject) {
        cadObj.archiveStatus = true;
        console.log(cadObj.archiveStatus);
        console.log(CAD_API + 'getNumber/' + cadObj.id + '/set-status');
        return this.http.post<CadObject>(CAD_API + 'getNumber/' + cadObj.id + '/set-status', cadObj)
            .subscribe(response => this.cad = cadObj);
    }


}
