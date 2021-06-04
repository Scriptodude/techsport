import moment, { Moment } from "moment"

export interface ApplicationConfigurationResponse {
  appMode: string
  pointModifiers: Map<string, number>
  startDate: string
  startDateMtl: Moment,
  endDate: string
  endDateMtl: Moment,
  supportedActivities: Map<string, string>
}

export function createDefaultConfigResponse(): ApplicationConfigurationResponse {
  return {
    appMode: "time",
    pointModifiers: new Map<string, number>(),
    startDate: '2021-01-01T00:00:00Z',
    startDateMtl: moment('2021-01-01T00:00:00Z').tz("America/Montreal"),
    endDate: '2021-01-01T00:00:00Z',
    endDateMtl: moment('2021-01-01T00:00:00Z').tz("America/Montreal"),
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