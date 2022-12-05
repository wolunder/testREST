
export class CadObject{
  id? : number ;
  farm : string  = '';
  cadNumber : string = '';
  typeOwn : string = '';
  tenancy : Date| undefined;
  area? : number
  cadCost? : number;
  agroArea? : number;
  shareOwn? : number;
  shareRent? : number;
  farmRent? : number;
  afOwnerArea?: number;
  notCultivated? : number;
  notDocument? : number;
  cause : string = '';
  note1 : string = '';
  archiveStatus: boolean | undefined;
  createDate : Date| undefined;


}
