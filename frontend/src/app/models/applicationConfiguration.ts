export interface ApplicationConfigurationResponse {
  appMode: String
  startDate: String
  endDate: String
}

export function createDefaultConfigResponse(): ApplicationConfigurationResponse {
  return {
    appMode: "time",
    startDate: '2021-01-01T00:00:00Z',
    endDate: '2021-01-01T00:00:00Z'
  }
}

export interface ApplicationConfigurationRequest {
  mode: String
  modifers: Map<String, Number>
  startDate: String
  endDate: String
}