export interface ApplicationConfigurationResponse {
  appMode: string
  startDate: string
  endDate: string
}

export function createDefaultConfigResponse(): ApplicationConfigurationResponse {
  return {
    appMode: "time",
    startDate: '2021-01-01T00:00:00Z',
    endDate: '2021-01-01T00:00:00Z'
  }
}

export interface ApplicationConfigurationRequest {
  mode: string
  modifiers: Map<string, number>
  startDate: string
  endDate: string
}


export function createDefaultConfigRequest(): ApplicationConfigurationRequest {
  return {
    mode: "",
    modifiers: new Map(),
    startDate: '2021-01-01T00:00:00Z',
    endDate: '2021-01-01T00:00:00Z'
  }
}