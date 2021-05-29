export interface ApplicationConfigurationResponse {
  appMode: string
  pointModifiers: Map<string, number>
  startDate: string
  endDate: string
  supportedActivities: Map<string, string>
}

export function createDefaultConfigResponse(): ApplicationConfigurationResponse {
  return {
    appMode: "time",
    pointModifiers: new Map<string, number>(),
    startDate: '2021-01-01T00:00:00Z',
    endDate: '2021-01-01T00:00:00Z',
    supportedActivities: new Map<string, string>()
  }
}

export interface ApplicationConfigurationRequest {
  mode: string
  modifiers: any
  startDate: string
  endDate: string
}


export function createDefaultConfigRequest(): ApplicationConfigurationRequest {
  return {
    mode: "",
    modifiers: new Map<string, number>(),
    startDate: '2021-01-01T00:00:00Z',
    endDate: '2021-01-01T00:00:00Z'
  }
}