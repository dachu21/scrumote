import {CustomMaterializeModule} from './custom-materialize.module';

describe('CustomMaterializeModule', () => {
  let customMaterializeModule: CustomMaterializeModule;

  beforeEach(() => {
    customMaterializeModule = new CustomMaterializeModule();
  });

  it('should create an instance', () => {
    expect(customMaterializeModule).toBeTruthy();
  });
});
