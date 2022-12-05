import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CadObject } from 'src/model/CadObject';


const CAD_API = 'http://localhost:9090/api/cad/'

// @Injectable({
//   providedIn: 'root'
// })
@Injectable()
export class CadService {

  constructor(private http: HttpClient) { }


  getAllCad(): Observable<CadObject[]>{
    console.log(CAD_API+'get-all');
    return  this.http.get<CadObject[]>(CAD_API+'get-all');
  }

  getObjectByCadNumber(cadnumber: string) :Observable<CadObject>{
    return this.http.get<CadObject>(CAD_API+'getNumber/'+cadnumber);
  }

  updateCadObject( obj : CadObject): Observable<CadObject>{
    console.log(CAD_API+'getNumber/'+obj.id+'/update-cad');
     return this.http.post<CadObject>(CAD_API+'getNumber/'+obj.id+'/update-cad', obj);
  }

  addCadObject(obj : CadObject): Observable<CadObject>{
    console.log(CAD_API+'/add-object');
    return this.http.post<CadObject>(CAD_API+'/add-object', obj);
  }

}
