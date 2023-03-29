export class CadObject {
  id?: number;
  farm: string = '';
  cadNumber: string = '';
  typeOwn: string = '';
  tenancy?: string = '';
  area: number = 0.0;
  cadCost: number = 0.0;
  agroArea: number = 0.0;
  shareOwn: number = 0.0;
  shareRent: number = 0.0;
  farmRent: number = 0.0;
  afOwnerArea: number = 0.0;
  notCultivated: number = 0.0;
  notDocument: number = 0.0;
  cause: string = '';
  note1: string = '';
  archiveStatus: boolean | undefined;
  createDate?: Date;
}
