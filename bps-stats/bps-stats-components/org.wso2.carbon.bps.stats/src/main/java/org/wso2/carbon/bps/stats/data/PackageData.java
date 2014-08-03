package org.wso2.carbon.bps.stats.data;

import org.apache.axis2.context.ConfigurationContext;
import org.wso2.carbon.bpel.core.BPELConstants;
import org.wso2.carbon.bpel.core.ode.integration.BPELServerImpl;
import org.wso2.carbon.bpel.core.ode.integration.store.TenantProcessStoreImpl;
import org.wso2.carbon.bpel.skeleton.ode.integration.mgt.services.BPELPackageManagementServiceSkeletonInterface;
import org.wso2.carbon.core.AbstractAdmin;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

import org.wso2.carbon.bpel.skeleton.ode.integration.mgt.services.PackageManagementException;
import org.wso2.carbon.bpel.skeleton.ode.integration.mgt.services.types.DeployedPackagesPaginated;
import org.wso2.carbon.bpel.skeleton.ode.integration.mgt.services.types.PackageType;
import org.wso2.carbon.bpel.skeleton.ode.integration.mgt.services.types.UndeployStatus_type0;
import org.wso2.carbon.bpel.core.ode.integration.store.repository.BPELPackageInfo;
import org.wso2.carbon.bpel.core.ode.integration.store.repository.BPELPackageRepository;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PackageData extends AbstractAdmin implements
		BPELPackageManagementServiceSkeletonInterface {

	private static Log log = LogFactory.getLog(PackageData.class);

	private TenantProcessStoreImpl getTenantProcessStore() {

		ConfigurationContext configContext = getConfigContext();
		Integer tenantId = MultitenantUtils.getTenantId(configContext);
		BPELServerImpl bpelServer = BPELServerImpl.getInstance();

		return (TenantProcessStoreImpl) bpelServer.getMultiTenantProcessStore()
				.getTenantsProcessStore(-1234);
	}

	public DeployedPackagesPaginated listDeployedPackagesPaginated(int page)
			throws PackageManagementException {

		int tPage = page;
		List<BPELPackageInfo> packages;
		DeployedPackagesPaginated paginatedPackages = new DeployedPackagesPaginated();
		TenantProcessStoreImpl tenantProcessStore = getTenantProcessStore();

		BPELPackageRepository packageRepo = tenantProcessStore
				.getBPELPackageRepository();
		try {
			packages = packageRepo.getBPELPackages(); // Can return null and we
														// should handle that
		} catch (Exception e) {
			String errorMessage = "Cannot get the BPEL Package list from repository.";
			log.error(errorMessage, e);
			throw new PackageManagementException(errorMessage, e);
		}

		if (packages != null) {
			// Calculating pagination information
			if (tPage < 0 || tPage == Integer.MAX_VALUE) {
				tPage = 0;
			}
			int startIndex = tPage * BPELConstants.ITEMS_PER_PAGE;
			int endIndex = (tPage + 1) * BPELConstants.ITEMS_PER_PAGE;

			int numberOfPackages = packages.size();
			int totalPackages = 0;
			BPELPackageInfo[] packagesArray = packages
					.toArray(new BPELPackageInfo[numberOfPackages]);
			
			log.debug("packages ------- "+ packagesArray);

//			for (int i = 0; i < numberOfPackages; i++) {
//				int count = getPackageVersionCount(packagesArray[i]);
//				if (totalPackages + count > startIndex
//						&& totalPackages < endIndex) {
//					// In-order to get the total number of packages count
//					// if (totalPackages >= endIndex) {
//					// break;
//					// }
//					int maxRemainingPackages = totalPackages < startIndex
//							&& (totalPackages + count) > startIndex ? startIndex
//							- (totalPackages + count)
//							: endIndex - totalPackages;
//					PackageType packageType = getPackageInfo(packagesArray[i],
//							maxRemainingPackages);
//					paginatedPackages.add_package(packageType);
//				}
//				totalPackages += count;
//			}
//			
//			int pages = (int) Math.ceil((double) totalPackages
//					/ BPELConstants.ITEMS_PER_PAGE);
			
			paginatedPackages.setPages(1);
		} else {
			// Returning empty result set with pages equal to zero for cases
			// where null is returned from
			// BPEL repo.
			paginatedPackages.setPages(0);
		}

		return paginatedPackages;
	}

	private int getPackageVersionCount(BPELPackageInfo bpelPackageInfo) {
		return bpelPackageInfo.getAvailableVersions().size();
	}

	public DeployedPackagesPaginated listDeployedPackagesPaginated(int arg0,
			String arg1) throws PackageManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	public PackageType listProcessesInPackage(String arg0)
			throws PackageManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	public UndeployStatus_type0 undeployBPELPackage(String arg0)
			throws PackageManagementException {
		// TODO Auto-generated method stub
		return null;
	}

}
