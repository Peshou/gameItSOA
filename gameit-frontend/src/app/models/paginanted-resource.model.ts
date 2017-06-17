import {Deserialization} from "./shared/deserialization.model";
export class PaginatedResource<T extends Deserialization> extends Deserialization {
  items:Array<T> = [];
  page: PageDetails;

  static itemsPerPage:number = 20;

  constructor(currentPage?:number, numPages?:number, totalCount?:number, items?:Array<T>) {
    super();
    this.page.number = currentPage ? currentPage : 1;

    if (numPages) {
      this.page.totalPages = numPages;
    }

    if (totalCount) {
      this.page.totalElements = totalCount;
    }

    if (items) {
      this.items = items;
    }
  }

  append(nextItemList:PaginatedResource<T>) {
    if (nextItemList) {
      this.items = this.items.concat(nextItemList.items);
      this.page.number = nextItemList.page.number;
      this.page.totalPages = nextItemList.page.totalPages;
      this.page.totalElements = nextItemList.page.totalElements;
    }
  }

  nextPage() {
    let nextPage = this.page.number + 1;
    return nextPage <= this.page.totalPages ? nextPage : null;
  }

  deserializeGeneric(json:any, clazz:any) {
    json.items.map((item:any) => {
      let deserialized_item = new clazz().deserialize(item);
      this.items.push(deserialized_item);
    });
    if(json.page.number) this.page.number = json.page.number;
    if(json.page.totalPages) this.page.totalPages = json.page.totalPages;
    if(json.page.totalElements) this.page.totalElements = json.page.totalElements;
    if(json.page.size) this.page.size = json.page.size;
    return this;
  }

  serialize(){
    return {items: this.items.map((item)=>{
      return item.serialize();
    })}
  }

}
interface PageDetails {
  size: number,
  totalElements: number,
  totalPages: number,
  number: number,
}
