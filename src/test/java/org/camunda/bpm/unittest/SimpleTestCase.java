/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.unittest;

import java.util.Arrays;
import java.util.List;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Daniel Meyer
 * @author Martin Schimak
 */
public class SimpleTestCase {

  @Rule public ProcessEngineRule rule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {"contract-ranking.dmn"})
  public void shouldEvaluateContractRankingDecision() {

    final DecisionService decisionService = rule.getDecisionService();

    final List<Contract> contracts =
        Arrays.asList(
            new Contract("contract-1", "O2", 50.0, 3, 7.99),
            new Contract("contract-2", "Vodafone", 42.2, 2, 7.99),
            new Contract("contract-3", "O2", 21.6, 2, 6.99),
            new Contract("contract-4", "Vodafone", 500.0, 2, 10.85),
            new Contract("contract-5", "Telekom", 300.0, 2, 16.24));

    final DmnDecisionResult result =
        decisionService
            .evaluateDecisionByKey("contractRanking")
            .variables(Variables.createVariables().putValue("contracts", contracts))
            .evaluate();

    System.out.println(result.getResultList());
  }

  public class Contract {

    private String name;
    private String network;
    private double networkSpeed;
    private double includedDataVolume;
    private double monthlyCost;

    public Contract(
        String name,
        String network,
        double networkSpeed,
        double includedDataVolume,
        double monthlyCost) {
      this.name = name;
      this.network = network;
      this.networkSpeed = networkSpeed;
      this.includedDataVolume = includedDataVolume;
      this.monthlyCost = monthlyCost;
    }

    public String getName() {
      return name;
    }

    public String getNetwork() {
      return network;
    }

    public double getNetworkSpeed() {
      return networkSpeed;
    }

    public double getIncludedDataVolume() {
      return includedDataVolume;
    }

    public double getMonthlyCost() {
      return monthlyCost;
    }
  }
}
